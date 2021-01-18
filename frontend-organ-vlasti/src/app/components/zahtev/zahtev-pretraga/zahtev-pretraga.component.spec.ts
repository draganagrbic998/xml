import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZahtevPretragaComponent } from './zahtev-pretraga.component';

describe('ZahtevPretragaComponent', () => {
  let component: ZahtevPretragaComponent;
  let fixture: ComponentFixture<ZahtevPretragaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ZahtevPretragaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ZahtevPretragaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
