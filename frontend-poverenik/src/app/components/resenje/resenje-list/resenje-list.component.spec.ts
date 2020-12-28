import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResenjeListComponent } from './resenje-list.component';

describe('ResenjeListComponent', () => {
  let component: ResenjeListComponent;
  let fixture: ComponentFixture<ResenjeListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResenjeListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResenjeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
