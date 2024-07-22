import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {
  QuestionDto,
  FileUploadDto,
  Image,
  ImageDto,
} from '../utility/constants';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  constructor(private http: HttpClient) {}

  postQuestion(data: QuestionDto) {
    const url = 'http://localhost:8080/api/chat/extended'; // replace with your Spring backend URL
    return this.http.post(url, data);
  }

  postImage(data: ImageDto): Observable<ArrayBuffer> {
    const url = 'http://localhost:8080/generate/image'; // replace with your Spring backend URL
    return this.http.post(url, data, { responseType: 'arraybuffer' });
  }

  postFile(fileUploadDto: FileUploadDto) {
    const formData = new FormData();
    formData.append('memoryId', fileUploadDto.memoryId.toString());
    formData.append('file', fileUploadDto.file);
    const url = 'http://localhost:8080/file/upload'; // replace with your Spring backend URL
    return this.http.post(url, formData, { responseType: 'text' });
  }
}
