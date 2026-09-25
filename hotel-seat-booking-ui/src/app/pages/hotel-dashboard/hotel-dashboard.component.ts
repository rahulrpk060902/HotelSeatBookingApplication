import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';   // ✅ ADD THIS
import { RouterOutlet, Router } from '@angular/router';




@Component({
  selector: 'app-hotel-dashboard',
  standalone: true,
  imports: [RouterOutlet,RouterModule],
  templateUrl: './hotel-dashboard.component.html',
  styleUrls: ['./hotel-dashboard.component.css']
})

export class HotelDashboardComponent {

  constructor(private router: Router) {}


  confirmLogout(): void {
    const confirmed = confirm('Do you want to logout?');

    if (confirmed) {
      this.logout();
    }
  }

  logout(): void {

    localStorage.removeItem('token');
    localStorage.removeItem('hotelId');

    this.router.navigate(['/home']);
  }
}
