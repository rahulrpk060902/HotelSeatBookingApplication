import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HotelService } from '../services/hotel.service'; 

@Component({
  selector: 'app-booked-seats',
  standalone: true,
  imports: [
    CommonModule   // 👈 needed for *ngIf, *ngFor, date pipe
  ],
  templateUrl: './booked-seats.component.html',
  styleUrl: './booked-seats.component.css'
})
export class BookedSeatsComponent implements OnInit {

  bookings: any[] = [];   // ✅ already initialized

  constructor(private hotelService: HotelService) {}

  ngOnInit(): void {
    this.loadBookings();
  }

  loadBookings() {
    this.hotelService.getHotelBookings().subscribe({
      next: (res: any[]) => {
        this.bookings = res;   // backend returns array
      },
      error: () => {
        this.bookings = [];
      }
    });
  }
}

