import { Component } from '@angular/core';
import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { HotelLoginComponent } from './pages/hotel-login/hotel-login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { HotelDashboardComponent } from './pages/hotel-dashboard/hotel-dashboard.component';
import { authGuard } from './guards/auth.guard';
import { HotelProfileComponent } from './hotel-profile/hotel-profile.component';
import { SeatAllocationComponent } from './seat-allocation/seat-allocation.component';
import { BookedSeatsComponent } from './booked-seats/booked-seats.component';
import { UserLoginComponent } from './pages/user-login/user-login.component';
import { UserSignupComponent } from './pages/user-signup/user-signup.component';
import { UserDashboardComponent } from './pages/user-dashboard/user-dashboard.component';
import { UserProfileComponent } from './pages/user-dashboard/user-profile/user-profile.component';  
import { ViewBookingsComponent } from './pages/user-dashboard/view-bookings/view-bookings.component';
import { BookingSlotsComponent } from './pages/user-dashboard/booking-slots/booking-slots.component';
import { HotelForgotPasswordComponent } from './pages/hotel-forgot-password/hotel-forgot-password.component';
import { HotelResetPasswordComponent } from './pages/hotel-reset-password/hotel-reset-password.component';
import { UserForgotPasswordComponent } from './pages/user-forgot-password/user-forgot-password.component';
import { UserResetPasswordComponent } from './pages/user-reset-password/user-reset-password.component';

export const routes: Routes = [

  { path: '', redirectTo: 'home', pathMatch: 'full' }, 

  { path: 'home', component: HomeComponent },

  { path: 'hotel-login', component: HotelLoginComponent },

  { path: 'hotel-signup', component: SignupComponent },

    {
    path: 'hotel-forgot-password',
    component: HotelForgotPasswordComponent
  },

  {
  path: 'hotel-reset-password',
  component: HotelResetPasswordComponent
},

  {
    path: 'hotel-dashboard',
    component: HotelDashboardComponent,
    canActivate: [authGuard],
    children: [

      { path: 'profile', component: HotelProfileComponent },

      { path: 'seats', component: SeatAllocationComponent },

      { path: 'bookings', component: BookedSeatsComponent },

      { path: '', redirectTo: 'profile', pathMatch: 'full' },

    ]
  },
  {
    path: 'hotel-dashboard/profile',
    component: HotelProfileComponent,
    canActivate: [authGuard]
  },
  
    /* USER */
  { path: 'user-login', component: UserLoginComponent },
  { path: 'user-signup', component: UserSignupComponent },

  {
  path: 'user-forgot-password',
  component: UserForgotPasswordComponent
},

{
  path: 'user-reset-password',
  component: UserResetPasswordComponent
},

  {
    path: 'user-dashboard',
    component: UserDashboardComponent,
    children: [
      { path: 'profile', component: UserProfileComponent },
      { path: 'booking-slots', component: BookingSlotsComponent },
      { path: 'view-bookings', component: ViewBookingsComponent },
      { path: '', redirectTo: 'profile', pathMatch: 'full' }
    ]
  },

  { path: '**', redirectTo: 'user-login' },

  {
  path: 'user/bookings',
  loadComponent: () =>
    import('./pages/user-dashboard/view-bookings/view-bookings.component')
      .then(m => m.ViewBookingsComponent)
}


        

];
