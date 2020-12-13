import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor() { }

  getBase64(file: Blob): Promise<string>{
    return new Promise((resolve, reject) => {
      const reader: FileReader = new FileReader();
      reader.onload = () => resolve(reader.result as string);
      reader.onerror = () => reject();
      reader.readAsDataURL(file);
    });
  }

}
