use std::fs;

fn part2(data: &str) {
    let mut pos = 0;
    let mut x = 1;
    for line in data.lines() {
        print!(
            "{}",
            if i32::abs(x - pos % 40) <= 1 {
                "#"
            } else {
                "."
            }
        );
        pos += 1;
        if pos % 40 == 0 {
            print!("\n");
        }
        if line != "noop" {
            print!(
                "{}",
                if i32::abs(x - pos % 40) <= 1 {
                    "#"
                } else {
                    "."
                }
            );
            pos += 1;
            if pos % 40 == 0 {
                print!("\n");
            }
            x += line[5..].parse::<i32>().unwrap();
        }
    }
}

fn part1(data: &str) -> i32 {
    let mut cycle = 0;
    let mut x = 1;
    let mut result = 0;
    for line in data.lines() {
        cycle += 1;
        if cycle % 40 == 20 {
            result += x * cycle;
        }
        if line != "noop" {
            cycle += 1;
            if cycle % 40 == 20 {
                result += x * cycle;
            }
            x += line[5..].parse::<i32>().unwrap();
        }
    }
    result
}

fn main() {
    let data = fs::read_to_string("day10.txt").unwrap();
    println!("{}", part1(&data));
    part2(&data);
}
