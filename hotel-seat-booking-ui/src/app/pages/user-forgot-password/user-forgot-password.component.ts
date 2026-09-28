import { Component, OnInit } from '@angular/core';
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
  selector: 'app-user-forgot-password',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule
  ],
  templateUrl: './user-forgot-password.component.html',
  styleUrls: ['./user-forgot-password.component.css']
})
export class UserForgotPasswordComponent implements OnInit {

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
      email: [
        '',
        [
          Validators.required,
          Validators.email
        ]
      ]
    });

  }

  onSubmit(): void {

    if (this.forgotPasswordForm.invalid) {

      this.forgotPasswordForm.markAllAsTouched();

      return;
    }

    const email =
      this.forgotPasswordForm.get('email')?.value
        .trim()
        .toLowerCase();

    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';

    console.log('USER FORGOT PASSWORD EMAIL:', email);

    this.authService.userForgotPassword(email).subscribe({

      next: (res: any) => {

  console.log('USER FORGOT PASSWORD RESPONSE:', res);

  this.loading = false;

  this.successMessage =
    'Temporary password sent to your registered email.';

  // Store email temporarily
  sessionStorage.setItem('resetEmail', email);

  setTimeout(() => {

    this.router.navigate(['/user-reset-password']);

  }, 1000);
},

      error: (err: any) => {

        console.error('USER FORGOT PASSWORD ERROR:', err);

        this.loading = false;

        if (err.status === 400 || err.status === 404) {

          this.errorMessage =
            err.error?.message ||
            'Email is not registered or something went wrong.';

        } else {

          this.errorMessage =
            err.error?.message ||
            'Something went wrong. Please try again.';
        }

      }

    });

  }

}