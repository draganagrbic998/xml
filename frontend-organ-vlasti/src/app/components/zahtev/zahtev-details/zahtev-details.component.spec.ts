import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZahtevDetailsComponent } from './zahtev-details.component';

describe('ZahtevDetailsComponent', () => {
  let component: ZahtevDetailsComponent;
  let fixture: ComponentFixture<ZahtevDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ZahtevDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ZahtevDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
