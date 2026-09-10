import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const credentials = sessionStorage.getItem('auth_credentials');

  if (!credentials) {
    return next(req);
  }

  const authReq = req.clone({
    setHeaders: {
      Authorization: `Basic ${credentials}`
    }
  });

  return next(authReq);
};
