import { TestBed } from '@angular/core/testing';

import { OdlukaService } from './odluka.service';

describe('OdlukaService', () => {
  let service: OdlukaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OdlukaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
