export interface Message {
  id: number;
  memoryId: number;
  sender: string;
  content: string;
  dateTime: Date;
}

export interface Image {
  id: number;
  memoryId: number;
  sender: string;
  content: string;
  dateTime: Date;
}

export interface QuestionDto {
  memoryId: number;
  question: string;
}

export interface ImageDto {
  memoryId: number;
  question: string;
}

export interface QuestionResponseDto {
  response: string;
}

export interface ImageResponseDto {
  response: ArrayBuffer;
}

export interface FileUploadDto {
  memoryId: number;
  file: File;
}

export interface FileUploadResponseDto {
  response: string;
}
