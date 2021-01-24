import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Pipe({
  name: 'safeHtml'
})
export class SafeHtmlPipe implements PipeTransform {

  constructor(
    private sanitizer: DomSanitizer
  ) {}

  transform(html: string): any {
    html = html.replace('http://localhost:4200', 'http://localhost:4201');
    return this.sanitizer.bypassSecurityTrustHtml(html);
  }

}
