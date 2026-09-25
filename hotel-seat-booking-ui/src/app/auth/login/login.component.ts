import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../auth.service';
import { RouterModule } from '@angular/router';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule,RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  email: string = '';
  password: string = '';

  constructor(private authService: AuthService) {}

  login() {

    const body = {
      email: this.email,
      password: this.password
    };

    this.authService.hotelLogin(body).subscribe({
  next: (res: any) => {
    localStorage.setItem('token', res.token);
    console.log("Login success");
  },
  error: (err: any) => {
    console.log("Login failed", err);
  }
});

  }

}
