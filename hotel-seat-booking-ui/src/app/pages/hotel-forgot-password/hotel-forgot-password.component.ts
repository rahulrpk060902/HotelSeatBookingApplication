import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-hotel-forgot-password',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule
  ],
  templateUrl: './hotel-forgot-password.component.html',
  styleUrls: ['./hotel-forgot-password.component.css']
})
export class HotelForgotPasswordComponent {

  forgotPasswordForm!: FormGroup;

  loading = false;
  errorMessage = '';
  successMessage = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {

    this.forgotPasswordForm = this.fb.group({
      email: ['', [
        Validators.required,
        Validators.email
      ]]
    });

  }

  onSubmit(): void {

    if (this.forgotPasswordForm.invalid) {
      return;
    }

    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';

    const email = this.forgotPasswordForm.value.email;

    this.authService.hotelForgotPassword(email).subscribe({

      next: (res: any) => {

  console.log('FORGOT PASSWORD RESPONSE', res);

  this.loading = false;

  // Store email for reset password page
  sessionStorage.setItem('hotelResetEmail', email);

  this.successMessage =
    'Temporary password has been sent to your registered email.';

  this.router.navigate(['/hotel-reset-password']);
},

      error: (err: any) => {

        console.error('FORGOT PASSWORD ERROR', err);

        this.loading = false;

        if (err.status === 404) {

          this.errorMessage =
            'This email is not registered as a hotel.';

        } else {

          this.errorMessage =
            'Hotel email is not registered.';
        }
      }

    });
  }
}