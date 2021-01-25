import { AfterViewInit, Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { XonomyService } from 'src/app/services/xonomy/xonomy.service';

declare const Xonomy: any;

@Component({
  selector: 'app-napredna-pretraga',
  templateUrl: './napredna-pretraga.component.html',
  styleUrls: ['./napredna-pretraga.component.sass']
})
export class NaprednaPretragaComponent implements AfterViewInit {

  constructor(
    private xonomyService: XonomyService,
    private router: Router
  ) { }

  @Input() fetchPending: boolean;
  @Output() pretragaTriggered: EventEmitter<string> = new EventEmitter();
  @Input() tip: string;

  get page(): string{
    return this.router.url.replace('-list', '');
  }

  pretrazi(): void{
    this.pretragaTriggered.emit(Xonomy.harvest());
  }

  ngAfterViewInit(): void{
  }

}
