export interface Zalba{
    tipZalbe: string;
    organVlasti: string;
    datumZahteva: Date;
    detalji: string;
    tipCutanja?: string;
    brojOdluke?: string;
    datumOdluke?: Date;
    kopijaZahteva: Blob;
    kopijaOdluke?: Blob;
}
