// import { Component } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { Router } from '@angular/router';
// import { CommonModule } from '@angular/common';
// import { FormsModule } from '@angular/forms';
// import { RouterModule } from '@angular/router'; // 🔥 ADD THIS


// @Component({
//   selector: 'app-user-signup',
//   standalone: true,
//   templateUrl: './user-signup.component.html',
//   imports: [CommonModule, FormsModule,RouterModule ],
//   styleUrls: ['./user-signup.component.css']


// })
// export class UserSignupComponent {

//   signup = {
//     name: '',
//     phone: '',
//     email: '',
//     place: '',
//     password: '',
//     confirmPassword: ''
//   };

//   constructor(private http: HttpClient, private router: Router) {}

//   signupUser() {
//     this.http.post(
//       'http://localhost:8080/api/auth/user/signup',
//       this.signup
//     ).subscribe({
//       next: (res: any) => {
//         this.router.navigate(['/user-login']); // 🔥 redirect after signup
//       },
//       error: (err) => {
//         alert(err.error?.message || 'Signup failed');
//       }
//     });
//   }
// }


import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-user-signup',
  standalone: true,
  templateUrl: './user-signup.component.html',
  imports: [CommonModule, FormsModule, RouterModule],
  styleUrls: ['./user-signup.component.css']
})
export class UserSignupComponent {

  signup = {
    name: '',
    phone: '',
    email: '',
    place: '',
    password: '',
    confirmPassword: ''
  };

  errorMessage = '';

  constructor(private http: HttpClient, private router: Router) {}

  signupUser() {

    this.errorMessage = '';

    // Phone validation (exactly 10 digits)
    const phoneRegex = /^[0-9]{10}$/;

    if (!phoneRegex.test(this.signup.phone)) {
      this.errorMessage = 'Phone number must be exactly 10 digits';
      return;
    }

    // Email validation
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!emailRegex.test(this.signup.email)) {
      this.errorMessage = 'Enter a valid email address';
      return;
    }

    // Password match validation
    if (this.signup.password !== this.signup.confirmPassword) {
      this.errorMessage = 'Password and Confirm Password do not match';
      return;
    }

    this.http.post(
      'http://localhost:8080/api/auth/user/signup',
      this.signup
    ).subscribe({
      next: (res: any) => {

        alert('Signup successful');

        this.router.navigate(['/user-login']);

      },
      error: (err) => {

        this.errorMessage =
          err.error?.message || 'Signup failed';

      }
    });
  }
}