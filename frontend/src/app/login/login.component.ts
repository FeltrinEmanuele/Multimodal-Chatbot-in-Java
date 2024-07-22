import { Component } from '@angular/core';
//import { RouterOutlet, RouterModule } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { SharedServiceService } from '../service/shared-service.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  message: string = '';
  id!: number;

  constructor(
    private authService: AuthService,
    private router: Router,
    private SharedServiceService: SharedServiceService
  ) {}

  login() {
    this.authService.login(this.username, this.password).subscribe(
      (response) => {
        this.id = response.id;
        this.SharedServiceService.changeId(this.id);
        this.message = 'Login successful!';
        this.router.navigate(['/chatbot'], {
          //queryParams: { id: response.id },
        });
      },
      (error) => {
        this.message = 'Login failed!';
      }
    );
  }
}
