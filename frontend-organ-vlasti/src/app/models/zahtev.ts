export interface Zahtev{
    ime: string;
    prezime: string;
    mesto: string;
    ulica: string;
    broj: number;
    detalji: string;
    kontakt: string;
    tipUvida?: string;
    tipDostave?: string;
    opisDostave?: string;
}
