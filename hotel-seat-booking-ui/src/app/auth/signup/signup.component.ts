import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common'; // ADD THIS




@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [FormsModule,RouterModule,CommonModule ],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {

  hotelName!: string;
  place!: string;
  email!: string;
  phone!: string;
  password!: string;
  confirmPassword!: string;

  showPassword = false;
  showConfirmPassword = false;


  constructor(private authService: AuthService,
    private router: Router   

  ) {}

  togglePassword(): void {
  this.showPassword = !this.showPassword;
}

toggleConfirmPassword(): void {
  this.showConfirmPassword = !this.showConfirmPassword;
}

  // signup() {

  //   const body = {
  //     hotelName: this.hotelName,
  //     email: this.email,
  //     phone: this.phone,
  //     password: this.password,
  //     confirmPassword: this.confirmPassword,
  //     place: this.place
  //   };

  //   this.authService.hotelSignup(body).subscribe({
  //     next: (res:any) => {
  //       console.log("Signup success", res);

  //       this.router.navigate(['/hotel-login']);
  //     },
  //     error: (err:any) => {
  //       console.log("Signup failed", err);
  //     }
  //   });
  // }


  signup() {

  // Password match validation
  if (this.password !== this.confirmPassword) {
    alert("Passwords do not match");
    return;
  }

  // Strong password validation
  const passwordPattern =
    /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&]).{8,}$/;

  if (!passwordPattern.test(this.password)) {
    alert(
      "Password must contain uppercase, lowercase, number and special character"
    );
    return;
  }

  // Phone validation
  const phonePattern = /^[6-9]\d{9}$/;

  if (!phonePattern.test(this.phone)) {
    alert("Enter valid 10 digit phone number");
    return;
  }

  // Email validation
  const emailPattern =
    /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

  if (!emailPattern.test(this.email)) {
    alert("Enter valid email");
    return;
  }

  const body = {
    hotelName: this.hotelName,
    email: this.email,
    phone: this.phone,
    password: this.password,
    confirmPassword: this.confirmPassword,
    place: this.place
  };

  this.authService.hotelSignup(body).subscribe({
    next: (res: any) => {
      console.log("Signup success", res);

      this.router.navigate(['/hotel-login']);
    },
    error: (err: any) => {
      console.log("Signup failed", err);
    }
  });
}

}
