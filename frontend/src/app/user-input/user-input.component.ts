import {
  Component,
  EventEmitter,
  Output,
  ViewChild,
  ElementRef,
  ChangeDetectorRef,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import {
  QuestionDto,
  FileUploadResponseDto,
  FileUploadDto,
  ImageDto,
} from '../utility/constants';
import { SharedServiceService } from '../service/shared-service.service';
import { DataService } from '../service/data.service';
import { CommonModule } from '@angular/common';
declare var webkitSpeechRecognition: any;

const ENTER_KEY_ASCII = 13;

@Component({
  selector: 'app-user-input',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './user-input.component.html',
  styleUrl: './user-input.component.scss',
})
export class UserInputComponent {
  @Output() sendMessageEmitter = new EventEmitter<QuestionDto>();
  @Output() sendImageEmitter = new EventEmitter<ImageDto>();
  message: string = '';
  id!: number;
  @ViewChild('inputTextField') inputTextField!: ElementRef;
  recognition = new webkitSpeechRecognition();

  constructor(
    private sharedServiceService: SharedServiceService,
    private dataService: DataService,
    private changeDetector: ChangeDetectorRef
  ) {
    this.sharedServiceService.currentId.subscribe((id) => (this.id = id));
  }

  createImage() {
    const imageDto: ImageDto = {
      memoryId: this.id,
      question: this.message,
    };

    this.sendImageEmitter.emit(imageDto);
    this.message = '';
    this.recognition.stop();
  }

  startListening() {
    if (!('webkitSpeechRecognition' in window)) {
      alert(
        'Your browser does not support speech recognition. Please update or switch to a compatible browser.'
      );
      return;
    }

    this.recognition.continuous = false;
    this.recognition.interimResults = true;

    this.recognition.onresult = (event: any) => {
      let interimTranscript = '';
      for (let i = event.resultIndex; i < event.results.length; i++) {
        const transcript = event.results[i][0].transcript;
        if (event.results[i].isFinal) {
          this.message += transcript;
        } else {
          interimTranscript += transcript;
        }
      }
      this.inputTextField.nativeElement.value =
        this.message + interimTranscript;
      this.changeDetector.detectChanges();
    };

    this.recognition.start();
  }

  sendMessage() {
    const questionDto: QuestionDto = {
      memoryId: this.id,
      question: this.message,
    };

    console.log(questionDto);
    this.sendMessageEmitter.emit(questionDto);
    this.message = '';
    this.recognition.stop();
  }

  onKeyUp($event: any) {
    if ($event.which === ENTER_KEY_ASCII) {
      this.sendMessage();
      this.message = '';
    }
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      const fileUploadDto: FileUploadDto = {
        memoryId: this.id,
        file: file,
      };
      const uploadModal = document.getElementById('uploadModal')!;
      const uploadModalContent = document.getElementById(
        'uploadModal-content'
      )!;
      uploadModal.style.display = 'block'; // Show modal
      uploadModalContent.textContent = 'File is being uploaded...';
      this.dataService.postFile(fileUploadDto).subscribe(
        (response: string) => {
          console.log(response);
          uploadModal.style.display = 'none'; // Hide modal
        },
        (error) => {
          uploadModalContent.textContent = 'Unsupported File Format.';
          setTimeout(() => {
            uploadModal.style.display = 'none'; // Hide modal after 5 seconds
          }, 3000);
        }
      );
    }
  }
}
