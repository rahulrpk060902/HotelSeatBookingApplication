import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HotelService } from '../services/hotel.service';
import { HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms'; 


@Component({
  selector: 'app-hotel-profile',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './hotel-profile.component.html',
  styleUrls: ['./hotel-profile.component.css']
})
export class HotelProfileComponent implements OnInit {

  profile: any;
  originalProfile: any;

  isEditMode = false;

  constructor(private hotelService: HotelService) {}

  ngOnInit(): void {
    const hotelId = localStorage.getItem('hotelId');

    if (!hotelId) {
      console.error('Hotel ID not found in storage');
      return;
    }

    this.hotelService.getHotelProfile(hotelId).subscribe({
      next: (res: any) => {
        this.profile = res.data ?? res;
      },
      error: (err: HttpErrorResponse) => {
        if (err.status === 403) {
          console.error('Unauthorized – token invalid or expired');
        } else {
          console.error('Failed to load profile', err);
        }
      }
    });
  }

  enableEdit(): void {
    this.originalProfile = { ...this.profile }; // backup
    this.isEditMode = true;
  }

  cancelEdit(): void {
    this.profile = { ...this.originalProfile };
    this.isEditMode = false;
  }

  // 💾 Save changes
  saveProfile(): void {

  const phonePattern = /^[6-9]\d{9}$/;

  if (!phonePattern.test(this.profile.phone)) {
    alert("Enter valid 10 digit phone number");
    return;
  }
    
    const hotelId = localStorage.getItem('hotelId');
    if (!hotelId) return;

    this.hotelService.updateHotelProfile(hotelId, this.profile).subscribe({
      next: () => {
        this.isEditMode = false;
        console.log('Profile updated successfully');
      },
      error: (err: HttpErrorResponse) => {
        console.error('Update failed', err);
      }
    });
  }
}
