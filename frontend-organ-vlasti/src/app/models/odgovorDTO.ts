import { Referenca } from './referenca';

export interface OdgovorDTO{
    broj: number;
    datum: string;
    datumZalbe: string;
    reference: Referenca[];
}

