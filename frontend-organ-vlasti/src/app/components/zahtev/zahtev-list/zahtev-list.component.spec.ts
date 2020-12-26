import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZahtevListComponent } from './zahtev-list.component';

describe('ZahtevListComponent', () => {
  let component: ZahtevListComponent;
  let fixture: ComponentFixture<ZahtevListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ZahtevListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ZahtevListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
