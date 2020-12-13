export interface Zalba{
    tipZalbe: string;
    ime: string;
    prezime: string;
    mesto: string;
    ulica: string;
    broj: number;
    detalji: string;
    kontakt: string;
    organVlasti: string;
    datumZahteva: Date;
    kopijaZahteva: Blob;
    kopijaOdluke?: Blob;
    tipCutanja?: string;
    brojOdluke?: string;
    datumOdluke?: Date;
}
