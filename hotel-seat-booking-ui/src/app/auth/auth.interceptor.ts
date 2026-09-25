import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  // SSR safe
  if (typeof window === 'undefined') {
    return next(req);
  }

  const token = localStorage.getItem('token'); // ✅ FIXED

  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  return next(req);
};
