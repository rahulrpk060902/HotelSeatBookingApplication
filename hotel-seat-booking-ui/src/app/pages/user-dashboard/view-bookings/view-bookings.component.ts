import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserProfileService } from '../../../services/user.service';

@Component({
  selector: 'app-view-bookings',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './view-bookings.component.html',
  styleUrls: ['./view-bookings.component.css']
})
export class ViewBookingsComponent implements OnInit {

  bookings: any[] = [];
  loading = false;

  constructor(private userService: UserProfileService) {}

  ngOnInit(): void {
    this.loadBookings();
  }

  loadBookings() {

    this.loading = true;

    this.userService.getUserBookings().subscribe({
      next: (response: any) => {
        this.bookings = response.data;
        this.loading = false;
      },
      error: (error: any) => {
        console.error(error);
        this.loading = false;
      }
    });

  }

}