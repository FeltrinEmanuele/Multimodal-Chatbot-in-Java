import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register-or-login',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './register-or-login.component.html',
  styleUrl: './register-or-login.component.scss',
})
export class RegisterOrLoginComponent {
  constructor(private router: Router) {}

  navigateTo(route: string) {
    this.router.navigate([`/${route}`]);
  }
}
