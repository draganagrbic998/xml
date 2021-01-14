import { MatTableDataSource } from '@angular/material/table';
import { Referenca } from './referenca';

export interface ZahtevDTO{
    tipZahteva: string;
    broj: number;
    datum: string;
    status: string;
    reference: MatTableDataSource<Referenca>;
}

