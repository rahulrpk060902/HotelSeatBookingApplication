import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  hotelSignup(body:any) {
    return this.http.post(`${this.baseUrl}/hotel/signup`, body);
  }

  hotelLogin(body:any) {
    return this.http.post(`${this.baseUrl}/login`, body);
  }

  getHotelProfile(hotelId: string) {

  const token = localStorage.getItem('token');

  return this.http.get(
    `http://localhost:8080/api/hotel/dashboard/profile/${hotelId}`,
    {
      headers: {
        Authorization: `Bearer ${token}`
      }
    }
  );
}


  userLogin(body:any) {
  return this.http.post('http://localhost:8080/api/auth/login', body);
}

hotelForgotPassword(email: string) {

  return this.http.post(
    'http://localhost:8080/api/auth/forgot-password',
    {
      email: email,
      role: 'HOTEL'
    }
  );

}

hotelResetPassword(request: any) {

  return this.http.post(
    'http://localhost:8080/api/auth/reset-password',
    request
  );

}


userForgotPassword(email: string) {
  return this.http.post(
    'http://localhost:8080/api/auth/forgot-password',
    {
      email: email,
      role: 'USER'
    }
  );
}

userResetPassword(request: any) {
  return this.http.post(
    'http://localhost:8080/api/auth/reset-password',
    request
  );
}


}
