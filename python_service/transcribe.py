from flask import Flask, request, send_file
import numpy as np
from PIL import Image
import torch
import io
import os
from diffusers import StableDiffusionPipeline
from transformers import pipeline
import ollama

app = Flask(__name__)

# Initialize StableDiffusionPipeline for image generation
model_id = "runwayml/stable-diffusion-v1-5"
sd_pipe = StableDiffusionPipeline.from_pretrained(model_id, torch_dtype=torch.float16)
#sd_pipe = StableDiffusion3Pipeline.from_pretrained("stabilityai/stable-diffusion-3-medium-diffusers", torch_dtype=torch.float16)
sd_pipe = sd_pipe.to("cuda")

# Initialize the ASR pipeline
asr_pipe = pipeline("automatic-speech-recognition", model="openai/whisper-large-v3")

@app.route('/generateImage', methods=['POST'])
def generate_image():
    # Get data from POST request
    data = request.get_json()

    # Check if 'prompt' is in data and is not None
    if 'prompt' not in data or data['prompt'] is None:
        return "Error: 'prompt' is missing or None in the request data", 400

    # Generate image using StableDiffusionPipeline
    image = sd_pipe(data['prompt']).images[0]

    # Convert PIL Image to in-memory file
    byte_arr = io.BytesIO()
    image.save(byte_arr, format='PNG')
    byte_arr.seek(0)

    # Return image as file
    return send_file(byte_arr, mimetype='image/png')

@app.route('/transcribe', methods=['POST'])
def transcribe_audio():
    audio_file = request.files['file']
    transcript = asr_pipe(audio_file.read())
    return transcript

@app.route('/extract_text', methods=['POST'])
def extract_text():
    image_file = request.files['file']
    image_path = "./" + image_file.filename
    image_file.save(image_path)

    # Use LLaVA model to extract text from the image
    res = ollama.chat(
        model="llava",
        messages=[
            {
                'role': 'user',
                'content': 'Describe this image in the most detailed way that you can. Also obtain the most information from this image as you can:',
                'images': [image_path]
            }
        ]
    )

    # Delete the image file after processing
    os.remove(image_path)

    return res['message']['content']

if __name__ == "__main__":
    app.run(port=5000)
