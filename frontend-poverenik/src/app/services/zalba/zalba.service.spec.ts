import { TestBed } from '@angular/core/testing';

import { ZalbaService } from './zalba.service';

describe('ZalbaService', () => {
  let service: ZalbaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ZalbaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
