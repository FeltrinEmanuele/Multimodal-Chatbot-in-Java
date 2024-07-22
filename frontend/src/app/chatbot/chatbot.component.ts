import { Component } from '@angular/core';
import { Message } from '../utility/constants';
import { HeaderComponent } from '../header/header.component';
import { MessagePanelComponent } from '../message-panel/message-panel.component';
import { UserInputComponent } from '../user-input/user-input.component';
import { DataService } from '../service/data.service';
import { QuestionDto } from '../utility/constants';
import {
  QuestionResponseDto,
  Image,
  ImageDto,
  ImageResponseDto,
} from '../utility/constants';

@Component({
  selector: 'app-chatbot',
  standalone: true,
  imports: [HeaderComponent, MessagePanelComponent, UserInputComponent],
  templateUrl: './chatbot.component.html',
  styleUrl: './chatbot.component.scss',
})
export class ChatbotComponent {
  constructor(private dataService: DataService) {}

  title = 'chatbot-in-java';
  data: Message[] = [];
  lastId = 0;

  getMessage(content: QuestionDto) {
    // create a new user message
    let messageObject: Message = {
      id: this.lastId++, // generate a new id
      memoryId: content.memoryId,
      sender: 'user',
      content: content.question,
      dateTime: new Date(),
    };

    // add the new user message to the messages array
    this.data.push(messageObject);

    console.log(
      'memoryId BEFORE POST REQUEST HAS BEEN SUBMITTED' +
        (content as QuestionDto).memoryId.toString()
    );

    // send a POST request to the server with the user message content
    this.dataService.postQuestion(content).subscribe((response: any) => {
      console.log(
        'DATA AFTER POST REQUEST HAS BEEN SUBMITTED' +
          (response as QuestionResponseDto).response.toString()
      );
      // create a new server message with the response
      const newServerMessage: Message = {
        id: this.lastId++, // generate a new id
        memoryId: content.memoryId,
        sender: 'server',
        content: (response as QuestionResponseDto).response, // convert the response to string
        dateTime: new Date(),
      };

      // add the new server message to the messages array
      this.data.push(newServerMessage);
    });
  }

  getImage(content: ImageDto) {
    // Create a new user message
    let imageObject: Image = {
      id: this.lastId++, // generate a new id
      memoryId: content.memoryId,
      sender: 'user',
      content: content.question,
      dateTime: new Date(),
    };

    // Add the new user message to the messages array
    this.data.push(imageObject);

    console.log(
      'memoryId BEFORE POST REQUEST HAS BEEN SUBMITTED ' + content.memoryId
    );
    console.log(
      'content BEFORE POST REQUEST HAS BEEN SUBMITTED ' + content.question
    );

    // Send a POST request to the server with the user message content
    this.dataService.postImage(content).subscribe({
      next: (response: ArrayBuffer) => {
        // Convert the byte array to a Base64 string
        let base64Image =
          'data:image/png;base64,' + arrayBufferToBase64(response);

        console.log('DATA AFTER POST REQUEST HAS BEEN SUBMITTED' + base64Image);

        // Create a new server image with the response
        const newServerImage: Image = {
          id: this.lastId++, // generate a new id
          memoryId: content.memoryId,
          sender: 'server',
          content: base64Image, // Use the Base64 string as the image content
          dateTime: new Date(),
        };

        // Add the new server message to the messages array
        this.data.push(newServerImage);
      },
      error: (err) => {
        console.error('Error generating image:', err);
      },
    });
  }
}
function arrayBufferToBase64(buffer: ArrayBuffer): string {
  let binary = '';
  const bytes = new Uint8Array(buffer);
  const len = bytes.byteLength;
  for (let i = 0; i < len; i++) {
    binary += String.fromCharCode(bytes[i]);
  }
  return window.btoa(binary);
}
