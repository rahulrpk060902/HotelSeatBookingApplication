import { Component, OnInit } from '@angular/core';
import { UserProfileService } from '../../../services/user.service'; 
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-booking-slots',
  standalone: true,   // ✅ Required for standalone Angular apps
  templateUrl: './booking-slots.component.html',
  imports: [CommonModule],   // ✅ Enables date pipe, ngIf, ngFor
  styleUrls: ['./booking-slots.component.css']
})
export class BookingSlotsComponent implements OnInit {

  availableSlots: any[] = [];
  loading: boolean = false;
  errorMessage: string = '';

  constructor(private userService: UserProfileService) {}

  ngOnInit(): void {
    this.loadAvailableSlots();
  }

  loadAvailableSlots() {
    this.loading = true;

    this.userService.getAvailableHotels().subscribe({
      next: (response) => {
        this.availableSlots = response.data;
        this.loading = false;
      },
      error: (error) => {
        console.error(error);
        this.errorMessage = 'Failed to load available booking slots';
        this.loading = false;
      }
    });
  }

  bookSlot(slot: any) {

  const scheduleId = slot.scheduleId;

  if (!scheduleId) {
    console.error("Schedule ID missing");
    return;
  }

  this.userService.bookSlot(scheduleId).subscribe({
    next: (response: any) => {
      console.log("Booking success", response);
      alert("Seat booked successfully!");
      this.loadAvailableSlots();
    },
    error: (error: any) => {
      console.error("Booking failed", error);
      alert("Booking failed!");
    }
  });

}

}
