import { Routes } from '@angular/router';
import { ChatbotComponent } from './chatbot/chatbot.component';
import { RegisterOrLoginComponent } from './register-or-login/register-or-login.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { AuthGuardService } from './service/auth-guard.service';

export const routes: Routes = [
  {
    path: 'chatbot',
    component: ChatbotComponent,
    title: 'Chatbot With Documents',
    canActivate: [AuthGuardService],
  },
  {
    path: 'register-or-login',
    component: RegisterOrLoginComponent,
    title: 'Register Or Login',
  },
  { path: 'register', component: RegisterComponent, title: 'Register' },
  {
    path: 'login',
    component: LoginComponent,
    title: 'Login',
  },
  { path: '', redirectTo: 'register-or-login', pathMatch: 'full' },
  { path: '**', redirectTo: 'register-or-login', pathMatch: 'full' },
];
