import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';



@Component({
  selector: 'app-user-sidebar',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './user-sidebar.component.html',
  styleUrls: ['./user-sidebar.component.css']
})
export class UserSidebarComponent {

constructor(private router: Router) {}


  confirmLogout(): void {
    const confirmed = confirm('Do you want to logout?');

    if (confirmed) {
      this.logout();
    }
  }

  logout(): void {

    // Clear local storage
    localStorage.removeItem('token');
    localStorage.removeItem('userId');

    // Redirect to login
    this.router.navigate(['/home']);
  }
}

