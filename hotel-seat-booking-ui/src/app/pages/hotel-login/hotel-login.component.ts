import { HotelDashboardComponent } from './../hotel-dashboard/hotel-dashboard.component';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../auth/auth.service';
import { Router, RouterModule } from '@angular/router';


@Component({
  selector: 'app-hotel-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './hotel-login.component.html',
  styleUrls: ['./hotel-login.component.css']
})
export class HotelLoginComponent {

  loginForm!: FormGroup;
  loading = false;
  errorMessage = '';
  showPassword = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  togglePassword(): void {
  this.showPassword = !this.showPassword;
}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onLogin(): void {
    if (this.loginForm.invalid) return;

    this.loading = true;

    this.authService.hotelLogin(this.loginForm.value).subscribe({
  next: (res: any) => {
    console.log('API RESPONSE', res);
    localStorage.setItem('token', res.token);
    localStorage.setItem('hotelId', res.id); // or res.hotelId
    this.router.navigateByUrl('/hotel-dashboard');

  },
  // error: (err: any) => {
  //   console.error('LOGIN ERROR', err);
  //   this.loading = false;
  // }

  error: (err: any) => {
  console.error('LOGIN ERROR', err);

  this.loading = false;

  // Show invalid credentials message
  if (err.status === 401 || err.status === 403) {
    this.errorMessage = 'Invalid credentials';
  } else {
    this.errorMessage = 'Login failed. Please try again';
  }
}
});

  }
}
