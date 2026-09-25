import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../auth/auth.service';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-user-login',
  standalone: true,
  imports: [FormsModule, RouterModule, CommonModule],
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.css']
})
export class UserLoginComponent {

  email = '';
  password = '';
  errorMessage = '';


  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  login() {

        this.errorMessage = '';


    const body = {
      email: this.email,
      password: this.password
    };

    this.authService.userLogin(body).subscribe({

      next: (res:any) => {

        console.log("USER Login success", res);

        // ✅ store user token separately
        localStorage.setItem('token', res.token);
        localStorage.setItem('userId', res.id);
    
        localStorage.setItem('role', 'USER');

        // redirect to user dashboard
        this.router.navigate(['/user-dashboard']);

      },

      error: (err) => {
        console.log("User login failed", err);
        this.errorMessage = 'Invalid credentials';

      }

    });
  }
}
