import java.util.Scanner;
import java.util.ArrayList;
public class Intcode {
    public long[] program; private ArrayList<Long> output;
    public ArrayList<Long> inputs; protected int numInputs;
    protected int paused;
    protected int relBase;
    private Scanner scan;
    public Intcode(String input) {
        String[] parse = input.split(",");
        program = new long[parse.length*2];
        for (int i = 0; i < parse.length; i++)
            program[i] = Long.parseLong(parse[i]);
        output = new ArrayList<Long>();
        scan = new Scanner(System.in);
        inputs = new ArrayList<>(); numInputs = 0;
        paused = 0;
        relBase = 0;
    }
    public long getInput() {
        if (numInputs < inputs.size())
            return inputs.get(numInputs);
        System.out.print("input: ");
        return scan.nextLong();
    }
    protected int getMode(int i, int mode) {
        int result = 0;
        if (mode == 0) 
            result = (int)program[i];
        if (mode == 1)
            result = i;
        if (mode == 2)
            result = relBase+(int)program[i];
        if (result >= program.length) {
            long[] newArr = new long[result*2l < Integer.MAX_VALUE/100 ? result*2 : Integer.MAX_VALUE/100];
            for (int j = 0; j < program.length; j++)
                newArr[j] = program[j];
            program = newArr;
        }
        return result;
    }
    public long[] execute() {
        long[] orig = program.clone();
        while (paused != -1)
            execute(paused);
        long[] result = program;
        program = orig;
        return result;
    }
    public long[] execute(int i) {
        while (i < program.length && i > -1) {
            String init = ""+program[i];
            int opcode = Integer.parseInt(init.length() >= 2 ? init.substring(init.length()-2) : ""+init.charAt(init.length()-1));
            int[] mode = new int[3];
            for (int j = 0; j < mode.length && init.length()-3-j >= 0; j++)
                mode[j] = Integer.parseInt(""+init.charAt(init.length()-3-j));
            int idx3 = getMode(i+3, mode[2]), idx2 = getMode(i+2, mode[1]), idx1 = getMode(i+1, mode[0]);
            if (opcode == 99) { // break
                paused = -1;
                break;
            } else if (opcode == 1) { // add
                program[idx3] = program[idx1] + program[idx2];
                i += 4;
            } else if (opcode == 2) { // multiply
                program[idx3] = program[idx1] * program[idx2]; 
                i += 4;
            } else if (opcode == 3) { // input
                program[idx1] = getInput(); 
                i += 2; numInputs++;
            } else if (opcode == 4) { // output
                output.add(program[idx1]); 
                i += 2;
                paused = i; break;
            } else if (opcode == 5) { // jump-if-true
                if (program[idx1] != 0)
                    i = (int)program[idx2];
                else
                    i += 3;
            } else if (opcode == 6) { // jump-if-false
                if (program[idx1] == 0)
                    i = (int)program[idx2];
                else
                    i += 3;
            } else if (opcode == 7) { // less than
                if (program[idx1] < program[idx2])
                    program[idx3] = 1;
                else
                    program[idx3] = 0;
                i += 4;
            } else if (opcode == 8) { // equals
                if (program[idx1] == program[idx2])
                    program[idx3] = 1;
                else
                    program[idx3] = 0;
                i += 4;
            } else if (opcode == 9) { // relative base offset
                relBase += program[idx1];
                i += 2;
            }
        }
        return program;
    }
    public long[] getOutput() {
        long[] result = new long[output.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = output.get(i);
        return result;
    }
    public long latestOutput() {
        if (output.size() == 0)
            return -1;
        return output.get(output.size()-1);
    }
    public int getNumInputs() {
        return numInputs;
    }
    public int getPaused() {
        return paused;
    }
    public void output() {
        String str = "output: ";
        if (output.size() > 0) {
            for (int i = 0; i < output.size()-1; i++)
                str += output.get(i) + ",";
            str += output.get(output.size()-1);
        }
        System.out.println(str);
    }
}