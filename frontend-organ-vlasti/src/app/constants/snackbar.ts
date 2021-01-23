import { MatSnackBarConfig } from '@angular/material/snack-bar';

export const SNACKBAR_CLOSE = 'Close';
export const SNACKBAR_ERROR = 'Desila se greška! Pokušajte ponovo.';
export const EMPTY_DETALJI = 'Morate uneti detalje!';

export const SNACKBAR_SUCCESS_OPTIONS: MatSnackBarConfig = {
    horizontalPosition: 'center',
    verticalPosition: 'top',
    panelClass: 'snackbar-success',
    duration: 2000
};

export const SNACKBAR_ERROR_OPTIONS: MatSnackBarConfig = {
    horizontalPosition: 'center',
    verticalPosition: 'top',
    panelClass: 'snackbar-error',
    duration: 2000
};
