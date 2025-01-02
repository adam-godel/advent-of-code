use std::fs;

fn part2(data: &str) -> i64 {
    let orig: Vec<(usize, i64)> = data
        .lines()
        .map(|i| i.parse::<i64>().unwrap())
        .enumerate()
        .collect();
    let mut file: Vec<(usize, i64)> = orig
        .clone()
        .iter()
        .map(|i| (i.0, i.1 * 811589153))
        .collect();
    for _ in 0..10 {
        let mut idx = 0;
        while idx < orig.len() {
            let to_rem = file
                .iter()
                .position(|&i| i == (orig[idx].0, orig[idx].1 * 811589153))
                .unwrap();
            let val = file.remove(to_rem);
            let rem = ((to_rem as i64) + (val.1 as i64)).rem_euclid(file.len() as i64) as usize;
            file.insert(rem, val);
            idx += 1;
        }
    }
    let idx = file.iter().position(|&i| i.1 == 0).unwrap();
    file[(idx + 1000) % file.len()].1
        + file[(idx + 2000) % file.len()].1
        + file[(idx + 3000) % file.len()].1
}

fn part1(data: &str) -> i32 {
    let orig: Vec<(usize, i32)> = data
        .lines()
        .map(|i| i.parse::<i32>().unwrap())
        .enumerate()
        .collect();
    let mut file = orig.clone();
    let mut idx = 0;
    while idx < orig.len() {
        let to_rem = file.iter().position(|&i| i == orig[idx]).unwrap();
        let val = file.remove(to_rem);
        let rem = ((to_rem as i32) + (val.1 as i32)).rem_euclid(file.len() as i32) as usize;
        file.insert(rem, val);
        idx += 1;
    }
    let idx = file.iter().position(|&i| i.1 == 0).unwrap();
    file[(idx + 1000) % file.len()].1
        + file[(idx + 2000) % file.len()].1
        + file[(idx + 3000) % file.len()].1
}

fn main() {
    let data = fs::read_to_string("day20.txt").unwrap();
    println!("{}\n{}", part1(&data), part2(&data));
}
