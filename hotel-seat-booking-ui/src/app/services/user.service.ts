import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserProfileService {

  private baseUrl = 'http://localhost:8080/user/profile';
  private hotelUrl = 'http://localhost:8080/api/user/hotels';


  constructor(private http: HttpClient) {}

  getUserProfile(userId: string): Observable<any> {
    const token = localStorage.getItem('token'); // JWT stored after login

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this.http.get(`${this.baseUrl}/${userId}`, { headers });
  }

  updateUserProfile(userId: string, payload: any) {
  return this.http.put<any>(
    `http://localhost:8080/user/profile/${userId}`,
    payload,
    {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    }
  );
}

 getAvailableHotels(): Observable<any> {
    const token = localStorage.getItem('token');

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this.http.get(`${this.hotelUrl}/available`, { headers });
  }

  bookSlot(scheduleId: string) {
  const token = localStorage.getItem('token');

  return this.http.post(
    `http://localhost:8080/api/user/bookings/schedule/${scheduleId}`,
    {},
    {
      headers: {
        Authorization: `Bearer ${token}`
      }
    }
  );
}


getUserBookings(): Observable<any> {

  const token = localStorage.getItem('token');

  const headers = new HttpHeaders({
    Authorization: `Bearer ${token}`
  });

  return this.http.get('http://localhost:8080/api/user/bookings', { headers });
}

}
