import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-paginator',
  templateUrl: './paginator.component.html',
  styleUrls: ['./paginator.component.sass']
})
export class PaginatorComponent implements OnInit {

  constructor() { }

  @Input() range = 10;
  rangeArray: number[] = [];
  step = 2;

  refreshArray(index: number): void{
    this.rangeArray = [];
    let start = index - this.step;
    if (start <= 0){
      start = 1;
    }
    let end = index + this.step;
    if (end > this.range){
      end = this.range;
    }
    if (end - start < 2 * this.step){
      if (start <= 1){
        end += 2 * this.step - (end - start);
      }
      else{
        start -= 2 * this.step - (end - start);
      }
    }
    if (start > 1){
      this.rangeArray.push(1);
      if (start - 1 > 1){
        this.rangeArray.push(0);
      }
    }
    for (let i = start; i <= end; ++i){
      this.rangeArray.push(i);
    }
    if (end < this.range){
      if (end + 1 < this.range){
        this.rangeArray.push(0);
      }
      this.rangeArray.push(this.range);
    }
  }

  ngOnInit(): void {
    this.refreshArray(this.range / 2);
  }

}
