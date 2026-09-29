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
  selector: 'app-user-reset-password',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule
  ],
  templateUrl: './user-reset-password.component.html',
  styleUrls: ['./user-reset-password.component.css']
})
export class UserResetPasswordComponent implements OnInit {

  resetPasswordForm!: FormGroup;

  loading = false;
  errorMessage = '';
  successMessage = '';

  email = '';

  showNewPassword = false;
  showConfirmPassword = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  toggleNewPassword(): void {
  this.showNewPassword = !this.showNewPassword;
}

toggleConfirmPassword(): void {
  this.showConfirmPassword = !this.showConfirmPassword;
}

  ngOnInit(): void {

  // Get email saved during forgot-password process
  this.email = sessionStorage.getItem('resetEmail') || '';

  console.log('USER RESET EMAIL:', this.email);

  this.resetPasswordForm = this.fb.group({

    temporaryPassword: [
      '',
      Validators.required
    ],

    newPassword: [
      '',
      [
        Validators.required,
        Validators.minLength(6)
      ]
    ],

    confirmPassword: [
      '',
      Validators.required
    ]

  });

}

  onSubmit(): void {

    if (this.resetPasswordForm.invalid) {

      this.resetPasswordForm.markAllAsTouched();

      return;
    }

    const formValue = this.resetPasswordForm.value;

    if (
      formValue.newPassword !==
      formValue.confirmPassword
    ) {

      this.errorMessage =
        'Passwords do not match.';

      return;
    }

    if (!this.email) {

      this.errorMessage =
        'Email information is missing. Please restart the forgot password process.';

      return;
    }

    this.loading = true;

    this.errorMessage = '';
    this.successMessage = '';

    const request = {

      email: this.email,

      role: 'USER',

      temporaryPassword:
        formValue.temporaryPassword,

      newPassword:
        formValue.newPassword,

      confirmPassword:
        formValue.confirmPassword

    };

    console.log(
      'USER RESET PASSWORD REQUEST:',
      request
    );

    this.authService
      .userResetPassword(request)
      .subscribe({

        next: (res: any) => {

  console.log('USER RESET PASSWORD RESPONSE:', res);

  this.loading = false;

  // Remove temporary reset email
  sessionStorage.removeItem('resetEmail');

  this.successMessage =
    'Password reset successfully. Redirecting to User Login...';

  setTimeout(() => {

    this.router.navigate(['/user-login']);

  }, 1500);

},

        error: (err: any) => {

          console.error(
            'USER RESET PASSWORD ERROR:',
            err
          );

          this.loading = false;

          if (err.status === 400) {

            this.errorMessage =
              err.error?.message ||
              'Invalid temporary password or password details.';

          } else if (err.status === 404) {

            this.errorMessage =
              'User email is not registered.';

          } else {

            this.errorMessage =
              err.error?.message ||
              'Password reset failed. Please try again.';

          }

        }

      });

  }

}