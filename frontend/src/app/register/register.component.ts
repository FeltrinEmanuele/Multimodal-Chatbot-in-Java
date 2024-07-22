//import { RouterOutlet, RouterModule } from '@angular/router';
import { Component } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent {
  username: string = '';
  password: string = '';
  repeatPassword: string = '';
  message: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  register() {
    if (this.passwordsMatch()) {
      this.authService.register(this.username, this.password).subscribe(
        (response) => {
          this.message = 'Registration successful!';
          this.router.navigate(['/login']);
        },
        (error) => {
          this.message = 'Registration failed!';
          console.error('Register component error ' + error);
        }
      );
    } else {
      this.message = 'Passwords do not match!';
    }
  }

  passwordsMatch() {
    return this.password === this.repeatPassword;
  }
}
