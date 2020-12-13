import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZalbaListComponent } from './zalba-list.component';

describe('ZalbaListComponent', () => {
  let component: ZalbaListComponent;
  let fixture: ComponentFixture<ZalbaListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ZalbaListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ZalbaListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
