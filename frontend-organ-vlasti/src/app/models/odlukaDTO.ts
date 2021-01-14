import { Referenca } from './referenca';

export interface OdlukaDTO{
    tipOdluke: string;
    broj: number;
    datum: string;
    datumZahteva: string;
    reference: Referenca[];
}
