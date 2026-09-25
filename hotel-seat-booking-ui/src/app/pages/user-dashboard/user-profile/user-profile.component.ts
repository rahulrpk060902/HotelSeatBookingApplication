import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { UserProfileService } from '../../../services/user.service'; 
import { HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';



@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  userProfile: any;
  loading = true;
  userId!: string;

  constructor(
    private userProfileService: UserProfileService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit(): void {

    // ✅ Only run in browser
    if (isPlatformBrowser(this.platformId)) {
      this.userId = localStorage.getItem('userId') || '';
      this.loadProfile();
    }
  }

  loadProfile() {
    if (!this.userId) {
      console.error('User ID not found');
      return;
    }

    this.userProfileService.getUserProfile(this.userId).subscribe({
  next: (res: any) => {
    this.userProfile = res.data; // 👈 THIS IS CRITICAL
    this.loading = false;
  },
  error: (err) => {
    console.error(err);
    this.loading = false;
  }
});

  }



  editMode = false;
editProfile: any = {};
errorMessage = '';


enableEdit() {
  this.editMode = true;
  this.editProfile = { ...this.userProfile }; // clone
    this.errorMessage = '';

}

cancelEdit() {
  this.editMode = false;
    this.errorMessage = '';

}

updateProfile() {

    const phoneRegex = /^[0-9]{10}$/;

  if (!phoneRegex.test(this.editProfile.phone)) {
    this.errorMessage = 'Phone number must be exactly 10 digits';
    return;
  }
  const userId = this.userProfile.id;

    if (!userId) {
    console.error('User ID not found', this.userProfile);
    return;
  }


  this.userProfileService.updateUserProfile(userId, this.editProfile)
  .subscribe({
    next: (res: any) => {
      this.userProfile = res.data ?? res;
      this.editMode = false;
              this.errorMessage = '';

    },
    error: (err: HttpErrorResponse) => {
      console.error('Update failed', err);
              this.errorMessage = 'Profile update failed';

    }
  });


}

}
