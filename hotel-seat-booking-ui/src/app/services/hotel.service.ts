import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HotelService {

  private baseUrl = 'http://localhost:8080/api/hotel/dashboard';

  constructor(private http: HttpClient) {}

  getHotelProfile(hotelId: string) {
  return this.http.get(
    `http://localhost:8080/api/hotel/dashboard/profile/${hotelId}`
  );
}

updateHotelProfile(hotelId: string, payload: any) {
    return this.http.put(
      `${this.baseUrl}/profile/${hotelId}`,
      payload
    );
}

createSeat(data: any) {
  return this.http.post<any>('http://localhost:8080/api/hotel/seats', data);
}

getSeats() {
  return this.http.get<any>('http://localhost:8080/api/hotel/seats');
}

getHotelBookings() {
  return this.http.get<any>(
    'http://localhost:8080/api/hotel/bookings',
    {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    }
  );
}


}
