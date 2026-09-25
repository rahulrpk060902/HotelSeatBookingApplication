import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HotelService } from '../services/hotel.service';

@Component({
  selector: 'app-seat-allocation',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './seat-allocation.component.html',
  styleUrls: ['./seat-allocation.component.css']
})
export class SeatAllocationComponent implements OnInit {

  seats: any[] = [];

  seatForm = {
    tableName: '',
    seatNumber: null,
    startTime: '',
    endTime: ''
  };

  constructor(private hotelService: HotelService) {}
  minDateTime!: string;

  ngOnInit(): void {
    this.loadSeats();

        const now = new Date();
    now.setSeconds(0, 0);

    this.minDateTime = this.formatForDateTimeLocal(now);
  }

  onStartTimeChange() {
    if (this.seatForm.startTime) {
      const start = new Date(this.seatForm.startTime);
      start.setHours(start.getHours() + 1); // +1 hour

      this.seatForm.endTime = this.formatForDateTimeLocal(start);
    }
  }

    private formatForDateTimeLocal(date: Date): string {
    const pad = (n: number) => n.toString().padStart(2, '0');

    return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}T${pad(date.getHours())}:${pad(date.getMinutes())}`;
  }


  loadSeats() {
    this.hotelService.getSeats().subscribe(res => {
      this.seats = res.data;
    });
  }

  addSeat() {
    this.hotelService.createSeat(this.seatForm).subscribe(() => {
      alert('Seat added successfully');
      this.seatForm = {
        tableName: '',
        seatNumber: null,
        startTime: '',
        endTime: ''
      };
      this.loadSeats(); // 🔥 refresh list
    });
  }
}
