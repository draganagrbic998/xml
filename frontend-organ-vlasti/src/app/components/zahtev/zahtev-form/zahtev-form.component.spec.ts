import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZahtevFormComponent } from './zahtev-form.component';

describe('ZahtevFormComponent', () => {
  let component: ZahtevFormComponent;
  let fixture: ComponentFixture<ZahtevFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ZahtevFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ZahtevFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
