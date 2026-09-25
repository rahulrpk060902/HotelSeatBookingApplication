import { Component } from '@angular/core';
import { RouterOutlet, RouterModule } from '@angular/router';
// import { UserSidebarComponent } from './user-sidebar.component';
import { UserSidebarComponent } from '../../layout/user-sidebar/user-sidebar.component';

import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    RouterOutlet,
    UserSidebarComponent
  ],
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent {}

