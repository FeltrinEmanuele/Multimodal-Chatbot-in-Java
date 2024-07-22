import {
  AfterViewChecked,
  Component,
  ElementRef,
  Input,
  ViewChild,
} from '@angular/core';
import { Message } from '../utility/constants';

@Component({
  selector: 'app-message-panel',
  standalone: true,
  imports: [],
  templateUrl: './message-panel.component.html',
  styleUrl: './message-panel.component.scss',
})
export class MessagePanelComponent implements AfterViewChecked {
  @Input() messages: Message[] = [];

  @ViewChild('scrollMe')
  private myScrollContainer!: ElementRef;

  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  scrollToBottom(): void {
    try {
      this.myScrollContainer.nativeElement.scrollTop =
        this.myScrollContainer.nativeElement.scrollHeight;
    } catch (err) {}
  }
}
