import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = () => {

  const router = inject(Router);
  const token = localStorage.getItem('token');

  // 🔍 Debug (remove later)
  console.log('AUTH GUARD TOKEN:', token);

  if (token) {
    return true;
  }

  router.navigateByUrl('/hotel-login');
  return false;
};


