
import java.util.Arrays;
import java.util.Random;
import java.lang.Math;


/*
 * 12			4 only					**
 * 32			4 and 5 only
 * 61, 112		3 only
 */

public class FormalSeatingGenetic {

	int POPULATION_SIZE 		= 200000; 	//45000
	int MATING_POOL_SIZE		= 20000;	//375		//15000		//12350
	int ADDITIONAL_RANDOMS		= 100;		//22		//100
	int NUM_DIRECT_ELITES		= 750;		//157		//700
	int MUTATION_PERCENTAGE		= 3;		//5			//3
	
	int NUM_PER_TABLE		= 10;
	int NUM_TABLES			= 13;
	int NUM_STUDENTS		= (NUM_PER_TABLE * NUM_TABLES);
	int NUM_PREFS			= 5;
	
	int MAX_STUDENT_NUM_WITH_PREFS_EXCLUSIVE = 124;

	Random rng;
	
	
	int population[][];
	
	int preferences[][] = {
			
			{	0	,	0	,	0	,	0	,	0	},		//0
			{	66	,	95	,	39	,	28	,	56	},		//1
			{	99	,	15	,	16	,	18	,	61	},		//2
			{	49	,	50	,	68	,	102	,	0	},		//3
			{	5	,	107	,	40	,	14	,	10	},		//4
			{	96	,	107	,	114	,	59	,	117	},		//5
			{	124	,	51	,	10	,	14	,	107	},		//6
			{	26	,	6	,	121	,	5	,	0	},		//7
			{	19	,	17	,	22	,	71	,	97	},		//8
			{	115	,	39	,	17	,	21	,	19	},		//9
			{	14	,	40	,	6	,	51	,	124	},		//10
			{	63	,	84	,	30	,	61	,	117	},		//11
			{	78	,	125	,	100	,	75	,	76	},		//12
			{	106	,	50	,	34	,	49	,	0	},		//13
			{	10	,	40	,	74	,	51	,	124	},		//14
			{	16	,	2	,	18	,	74	,	36	},		//15
			{	15	,	18	,	2	,	81	,	74	},		//16
			{	19	,	8	,	101	,	53	,	45	},		//17
			{	16	,	15	,	2	,	74	,	36	},		//18
			{	17	,	8	,	22	,	71	,	101	},		//19
			{	47	,	32	,	128	,	67	,	0	},		//20
			{	29	,	63	,	32	,	102	,	20	},		//21
			{	8	,	24	,	71	,	97	,	19	},		//22
			{	0	,	0	,	0	,	0	,	0	},		//23
			{	22	,	71	,	8	,	28	,	97	},		//24
			{	20	,	99	,	94	,	68	,	0	},		//25
			{	32	,	36	,	35	,	61	,	74	},		//26
			{	98	,	32	,	21	,	75	,	103	},		//27
			{	93	,	34	,	43	,	24	,	18	},		//28
			{	63	,	21	,	32	,	11	,	102	},		//29
			{	84	,	67	,	47	,	20	,	56	},		//30
			{	91	,	89	,	33	,	79	,	81	},		//31
			{	20	,	21	,	63	,	27	,	26	},		//32
			{	89	,	91	,	31	,	79	,	81	},		//33
			{	50	,	126	,	106	,	13	,	0	},		//34
			{	81	,	84	,	61	,	96	,	15	},		//35
			{	74	,	15	,	18	,	63	,	128	},		//36
			{	59	,	118	,	119	,	114	,	0	},		//37
			{	63	,	29	,	20	,	74	,	128	},		//38
			{	95	,	93	,	1	,	66	,	56	},		//39
			{	10	,	14	,	117	,	51	,	124	},		//40
			{	42	,	52	,	75	,	54	,	55	},		//41
			{	41	,	52	,	75	,	54	,	55	},		//42
			{	18	,	28	,	67	,	65	,	92	},		//43
			{	53	,	45	,	69	,	112	,	113	},		//44
			{	44	,	53	,	105	,	69	,	86	},		//45
			{	56	,	76	,	77	,	78	,	126	},		//46
			{	20	,	67	,	30	,	63	,	29	},		//47
			{	26	,	107	,	32	,	4	,	124	},		//48
			{	50	,	50	,	50	,	50	,	50	},		//49
			{	49	,	34	,	106	,	13	,	0},			//50
			{	124	,	82	,	40	,	10	,	6	},		//51
			{	42	,	75	,	54	,	55	,	41	},		//52
			{	44	,	45	,	69	,	105	,	113	},		//53
			{	55	,	42	,	41	,	52	,	75	},		//54
			{	54	,	42	,	41	,	52	,	75	},		//55
			{	46	,	76	,	95	,	66	,	126	},		//56
			{	60	,	122	,	104	,	108	,	123	},		//57
			{	0	,	0	,	0	,	0	,	0	},		//58
			{	114	,	5	,	107	,	37	,	0	},		//59
			{	122	,	57	,	104	,	108	,	94	},		//60
			{	63	,	84	,	81	,	36	,	96	},		//61
			{	105	,	86	,	113	,	69	,	112	},		//62
			{	29	,	11	,	38	,	84	,	30	},		//63
			{	112	,	86	,	113	,	45	,	76	},		//64
			{	92	,	43	,	0	,	0	,	0	},		//65
			{	1	,	95	,	56	,	39	,	28	},		//66
			{	30	,	20	,	47	,	90	,	128	},		//67
			{	109	,	88	,	83	,	3	,	99	},		//68
			{	105	,	113	,	44	,	45	,	53	},		//69
			{	42	,	54	,	52	,	41	,	80	},		//70
			{	22	,	24	,	8	,	0	,	0	},		//71
			{	104	,	111	,	4	,	121	,	123	},		//72
			{	83	,	88	,	99	,	68	,	25	},		//73
			{	36	,	15	,	18	,	63	,	128	},		//74
			{	42	,	52	,	41	,	55	,	54	},		//75
			{	46	,	126	,	112	,	105	,	113	},		//76
			{	46	,	78	,	76	,	57	,	127	},		//77
			{	77	,	46	,	100	,	76	,	101	},		//78
			{	91	,	31	,	89	,	33	,	14	},		//79
			{	70	,	42	,	52	,	41	,	54	},		//80
			{	35	,	31	,	33	,	16	,	61	},		//81
			{	124	,	51	,	40	,	14	,	91	},		//82
			{	68	,	109	,	88	,	110	,	73	},		//83
			{	30	,	63	,	29	,	11	,	87	},		//84
			{	0	,	0	,	0	,	0	,	0	},		//85
			{	62	,	112	,	105	,	69	,	113	},		//86
			{	126	,	102	,	97	,	34	,	0	},		//87
			{	99	,	73	,	83	,	68	,	0	},		//88
			{	31	,	91	,	79	,	33	,	0	},		//89
			{	67	,	20	,	47	,	30	,	15	},		//90
			{	31	,	89	,	33	,	79	,	96	},		//91
			{	65	,	43	,	0	,	0	,	0	},		//92
			{	28	,	34	,	39	,	97	,	0	},		//93
			{	103	,	116	,	125	,	100	,	78	},		//94
			{	39	,	56	,	1	,	66	,	87	},		//95
			{	119	,	117	,	5	,	114	,	35	},		//96
			{	87	,	126	,	49	,	102	,	0	},		//97
			{	105	,	113	,	55	,	42	,	62	},		//98
			{	2	,	88	,	25	,	68	,	83	},		//99
			{	125	,	103	,	78	,	94	,	101	},		//100
			{	125	,	127	,	44	,	45	,	46	},		//101
			{	97	,	49	,	3	,	87	,	68	},		//102
			{	94	,	100	,	125	,	39	,	66	},		//103
			{	122	,	57	,	60	,	108	,	0	},		//104
			{	62	,	113	,	69	,	45	,	112	},		//105
			{	13	,	97	,	34	,	126	,	0	},		//106
			{	5	,	59	,	32	,	48	,	0	},		//107
			{	122	,	57	,	60	,	104	,	0	},		//108
			{	83	,	73	,	88	,	68	,	99	},		//109
			{	83	,	99	,	109	,	88	,	73	},		//110
			{	118	,	119	,	121	,	123	,	72	},		//111
			{	45	,	69	,	113	,	53	,	105	},		//112
			{	112	,	105	,	45	,	62	,	69	},		//113
			{	5	,	96	,	117	,	59	,	107	},		//114
			{	9	,	17	,	19	,	0	,	0	},		//115
			{	94	,	103	,	0	,	0	,	0	},		//116
			{	40	,	32	,	29	,	11	,	96	},		//117
			{	119	,	121	,	111	,	123	,	26	},		//118
			{	118	,	96	,	121	,	111	,	123	},		//119
			{	0	,	0	,	0	,	0	,	0	},		//120
			{	119	,	118	,	111	,	72	,	26	},		//121
			{	57	,	108	,	60	,	104	,	98	},		//122
			{	125	,	101	,	111	,	72	,	100	},		//123
			{	6	,	51	,	82	,	10	,	14	},		//124	DID NOT SUBMIT!!
			{	100	,	101	,	123	,	12	,	94	},		//125	DID NOT SUBMIT!!
			{	87	,	34	,	76	,	97	,	106	},		//126	DID NOT SUBMIT!!
			{	101	,	77	,	0	,	0	,	0	},		//127	DID NOT SUBMIT!!
			{	20	,	36	,	38	,	67	,	74	},		//128	DID NOT SUBMIT!!
			{	0	,	0	,	0	,	0	,	0	},		//129

	};
	
	//bottom value inclusive, top value exclusive)
	private int getRandom(int min, int max) {
		return rng.nextInt(max - min) + min;
	}
	
	private int getDifferentRandom(int min, int max, int notThis) {
		int r;
		do {
			r = getRandom(min, max);
		} while (r == notThis);
		return r;
	}
	
	private int[] createRandomArrangement() {
		int[] arr = new int[NUM_STUDENTS];
		
		for (int i = 0; i < NUM_STUDENTS; ++i) {
			arr[i] = i;
		}
		
		for (int i = 0; i < NUM_STUDENTS * 2; ++i) {
			int s1 = getRandom(0, NUM_STUDENTS);
			int s2 = getDifferentRandom(0, NUM_STUDENTS, s1);
			
			int temp = arr[s1];
			arr[s1] = arr[s2];
			arr[s2] = temp;
		}
		
		return arr;
	}
	
	private int getDuds(int arr[]) {
		int tduds = 0;
		for (int i = 0; i < NUM_TABLES; ++i) {
			int duds = 0;
			for (int j = 0; j < NUM_PER_TABLE; ++j) {
				if (arr[i * NUM_PER_TABLE + j] == 0) ++duds;
				if (arr[i * NUM_PER_TABLE + j] == 23) ++duds;
				if (arr[i * NUM_PER_TABLE + j] == 58) ++duds;
				if (arr[i * NUM_PER_TABLE + j] == 85) ++duds;
				if (arr[i * NUM_PER_TABLE + j] == 120) ++duds;
				if (arr[i * NUM_PER_TABLE + j] == 129) ++duds;
			}
			if (duds > 1) {
				tduds += duds * duds * 15;
			}
		}
		return tduds;
	}
	
	private int getIndividualFitness(int arr[], int seatNum) {
		int tableBase = (seatNum / NUM_PER_TABLE) * NUM_PER_TABLE;
		int person = arr[seatNum];
		
		int fitness = 0;
		
		for (int i = tableBase; i < tableBase + NUM_PER_TABLE; ++i) {
			int numZeros = 0;
			for (int j = 0; j < NUM_PREFS; ++j) {
				if (preferences[person][j] == 0) {
					++numZeros;
				}
				if (preferences[person][j] == arr[i]) {
					fitness += (NUM_PREFS - j);
				}
			}
			if (numZeros == NUM_PREFS) return -1;
		}
		
		int maxPossibleFitness = (NUM_PREFS + 1) * NUM_PREFS / 2;
		if (fitness >= maxPossibleFitness * 2 / 3) {
			fitness = maxPossibleFitness;
		}
				
		return fitness;
	}
	
	private boolean[] getIndividualFitnessByParts(int arr[], int seatNum) {
		int tableBase = (seatNum / NUM_PER_TABLE) * NUM_PER_TABLE;
		int person = arr[seatNum];
		
		boolean parts[] = new boolean[NUM_PREFS];
		
		for (int i = tableBase; i < tableBase + NUM_PER_TABLE; ++i) {
			for (int j = 0; j < NUM_PREFS; ++j) {
				if (preferences[person][j] == arr[i]) {
					parts[j] = true;
				}
			}
		}
				
		return parts;
	}
	
	private void displayPerPersonStatistics(int arr[]) {
		int with5 = 0;
		int with4 = 0;
		int with3 = 0;
		int with2 = 0;
		int with1 = 0;
		int with = 0;
		int with2A = 0;
		int with1A = 0;
		int with3A = 0;
		int with4A = 0;
		int with5A = 0;
				
		System.out.printf("     ");
		for (int i = 0; i < NUM_PREFS; ++i) {
			System.out.printf("%d ", i + 1);
		}
		System.out.printf("\n");
		for (int i = 0; i < NUM_STUDENTS; ++i) {
			boolean[] parts = getIndividualFitnessByParts(arr, i);
			boolean hasPref = false;
			
			for (int j = 0; j < NUM_PREFS; ++j) { 
				if (preferences[arr[i]][j] != 0) {
					hasPref = true;
					break;
				}
			}
				
			if (hasPref) {
				with++;
				if (parts[0]) with1++;
				if (parts[0] || parts[1]) with2++;
				if (parts[0] || parts[1] || parts[2]) with3++;
				if (parts[0] || parts[1] || parts[2] || parts[3]) with4++;
				if (parts[0] || parts[1] || parts[2] || parts[3] || parts[4]) with5++;

				System.out.printf("%3d: ", arr[i]);
				int numTrues = 0;
				for (int j = 0; j < NUM_PREFS; ++j) { 
					System.out.printf(parts[j] ? "Y " : "N ");
					if (parts[j]) numTrues += 1;
				}
				if (numTrues >= 1) with1A++;
				if (numTrues >= 2) with2A++;
				if (numTrues >= 3) with3A++;
				if (numTrues >= 4) with4A++;
				if (numTrues >= 5) with5A++;

				System.out.printf("\n");
				
			} else if (arr[i] != 0 && arr[i] != 23 && arr[i] != 58 && arr[i] != 85 && arr[i] != 120 && arr[i] != 129) {
				System.out.printf("%3d: did not submit prefs\n", arr[i]);
			}
		}
		
		System.out.printf("\n%d%% have their 5th or better\n", with5 * 100 / with);
		System.out.printf("%d%% have their 4th or better\n", with4 * 100 / with);
		System.out.printf("%d%% have their 3rd or better\n", with3 * 100 / with);
		System.out.printf("%d%% have their 2nd or better\n", with2 * 100 / with);
		System.out.printf("%d%% have their 1st or better\n\n", with1 * 100 / with);
		
		System.out.printf("%d%% have at least 1 preference\n", with1A * 100 / with);
		System.out.printf("%d%% have at least 2 preferences\n", with2A * 100 / with);
		System.out.printf("%d%% have at least 3 preferences\n", with3A * 100 / with);
		System.out.printf("%d%% have at least 4 preferences\n", with4A * 100 / with);
		System.out.printf("%d%% have all 5 preferences\n\n", with5A * 100 / with);

		System.out.printf("with = %d\n\n", with);
	}
	
	private int getFitness(int arr[]) {
		int fitness = 0;
		int sadness = 0;
		boolean gotLonely = false;
		int numLonely = 0;
		
		int perTableFitness = 0;
		double perTableFitnessEqualityAdjusted = 1;
		
		for (int i = 0; i < NUM_STUDENTS; ++i) {
			int fit = getIndividualFitness(arr, i);
			if (fit <= 3) {
				sadness += (6 - fit) * (6 - fit);
			}
			if (fit == 0) {
				++numLonely;
				gotLonely = true;
			}
			fitness += (int) Math.sqrt((double) fit * 100);
			perTableFitness += (int) Math.sqrt((double) fit * 100);
			if (fit < 0) fit = 0;
			perTableFitnessEqualityAdjusted *= (double) (fit + 1);
			
			if (i % NUM_PER_TABLE == NUM_PER_TABLE - 1) {
				
				int numOnTable = NUM_PER_TABLE;
				for (int j = 0; j < NUM_PER_TABLE; ++j) {
					int c = arr[i - NUM_PER_TABLE + 1 + j];
					if (c == 0 || c == 23 || c == 58 || c == 85 || c == 120 || c == 129) {
						numOnTable--;
					}
				}
				
				perTableFitnessEqualityAdjusted = Math.pow(80000.0 * perTableFitnessEqualityAdjusted, 1.0 / ((double)numOnTable));

				//System.out.printf("Table %d fitness per capita (%d): %d. Equality: %f\n", i / 10 + 1, numOnTable, perTableFitness / numOnTable, perTableFitnessEqualityAdjusted / numOnTable);
				perTableFitness = 0;
				perTableFitnessEqualityAdjusted = 1;
			}
		}

		int numSplit = getNumSplitCouples(arr);
		
		if (numSplit <= 3) {fitness *= 65; fitness /= 64;}
		if (numSplit <= 1) {fitness *= 33; fitness /= 32;}
		if (numLonely <= 3) {fitness *= 129; fitness /= 128;}
		if (numLonely <= 1) {fitness *= 33; fitness /= 32;} 
		if (numLonely == 0) {fitness *= 17; fitness /= 16;} 

		if (numSplit <= numLonely * 2 + 8 || (numLonely <= 3 && numSplit <= 10)) {fitness *= 17; fitness /= 16;}

		fitness -= getDuds(arr);
		
		if (fitness < 10) return 10;
		
		return fitness;
	}
	
	private int getFitness2(int arr[]) {
		int fitness = 0;
		boolean gotLonely = false;
		for (int i = 0; i < NUM_STUDENTS; ++i) {
			int fit = getIndividualFitness(arr, i);
			if (fit == 0) {
				gotLonely = true;
			}
			fitness += (int) Math.sqrt((double) fit * 100);
		}

		int numSplit = getNumSplitCouples(arr);
		if (numSplit != 0) {
			fitness *= 9;
			fitness /= 8;
		}
		if (gotLonely) {
			fitness *= 9; 
			fitness /= 8;
		}
		
		fitness -= getDuds(arr);
		
		return fitness;
		
		/*int fitness = 0;
		for (int i = 0; i < NUM_STUDENTS; ++i) {
			int fit = getIndividualFitness(arr, i);
			fitness += fit * fit;
		}
		return fitness;*/
	}
	
	private int[] findBestInPopulation(int count, int evalList[]) {
		int indicies[] = new int[count];
		
		int index = 0;
		while (index < count) {
			int best = 0;

			for (int i = 0; i < POPULATION_SIZE; ++i) {
				if (evalList[i] > evalList[best]) {
					boolean found = false;
					for (int j = 0; j < index; ++j) {
						if (indicies[j] == i) {
							found = true;
							break;
						}
					}
					if (!found) {
						best = i;
					}
				}
 			}
						
			indicies[index++] = best;
		}
		
		
		return indicies;
	}
	
	public int[] getArrangementStatistics(int arr[]) {
		int lonelyPeople = 0;
		int happyPeople = 0;
		int extrHappy = 0;
		int extrLonely = 0;
		for (int i = 0; i < NUM_STUDENTS; ++i) {
			int fitness = getIndividualFitness(arr, i);
			if (fitness == -1) continue;
			if (fitness == 0) ++extrLonely;
			if (fitness <= 3) ++lonelyPeople;
			if (fitness >= 8) ++happyPeople;
			if (fitness == 15) ++extrHappy;
		}
		
		return new int[] {extrLonely, lonelyPeople, happyPeople, extrHappy, arr.length};
	}
	
	private void displayArrangementStatistics(int arr[]) {		
		int lonelyPeople = 0;
		int happyPeople = 0;
		int extrHappy = 0;
		int extrLonely = 0;
		int avg = 0;
		for (int i = 0; i < NUM_STUDENTS; ++i) {
			int fitness = getIndividualFitness(arr, i);
			if (fitness == -1) continue;
			avg += fitness;
			if (fitness == 0) ++extrLonely;
			if (fitness <= 3) ++lonelyPeople;
			if (fitness >= 8) ++happyPeople;
			if (fitness == 15) ++extrHappy;
		}
		avg /= NUM_STUDENTS;
				
		int extrLonelyPercent = extrLonely * 100 / NUM_STUDENTS;
		if (extrLonelyPercent == 0 && extrLonely != 0) {
			extrLonelyPercent = 1;
		}
		System.out.printf("Fitness: %d\t\t(%d)\n", getFitness(arr), getFitness2(arr));
		System.out.printf("Average: %d\n", avg);

		System.out.printf("Lonely people (fitness <= 3): %d%% (%d%% have none)\n", 
				lonelyPeople * 100 / NUM_STUDENTS,
				extrLonelyPercent);
		System.out.printf("Happy  people (fitness >= 8): %d%% (%d%% have all)\n", 
				happyPeople * 100 / NUM_STUDENTS,
				extrHappy * 100 / NUM_STUDENTS);
		System.out.printf("\n");
	}

	
	private int findIndexOfPersonInArrangement(int[] arr, int person) {
		for (int i = 0; i < NUM_STUDENTS; ++i) {
			if (arr[i] == person) return i;
		}
		return -1;
	}
	
	private boolean isPersonInArrangement(int arr[], int person) {
		return findIndexOfPersonInArrangement(arr, person) != -1;
	}
	
	private int[] cycleCrossover(int p1[], int p2[]) {
		int child[] = new int[NUM_STUDENTS];
		for (int i = 0; i < NUM_STUDENTS; ++i) {
			child[i] = -1;
		}
		
		int index = 0;
		while (!isPersonInArrangement(child, p1[index])) {
			child[index] = p1[index];
			index = findIndexOfPersonInArrangement(p1, p2[index]);
		}
		
		int parentIndex = 0;
		for (int i = 0; i < NUM_STUDENTS; ++i) {
			if (child[i] == -1) {
				while (isPersonInArrangement(child, p2[parentIndex])) {
					parentIndex++;
				}
				child[i] = p2[parentIndex++];
			}
		}
		
		return child;
	}
	
	private int[][] getElites(int size, int evalList[]) {
		int pool[][] = new int[size][NUM_STUDENTS];
		int indicies[] = findBestInPopulation(size, evalList);
		
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < NUM_STUDENTS; ++j) {
				pool[i][j] = population[indicies[i]][j];
			}
		}

		return pool;
	}
	
	private int[][] stochasticUniversalSampling(int size, int evalList[]) {
		int pool[][] = new int[size][NUM_STUDENTS];
		
		int totalFitness = 0;
		for (int i = 0; i < POPULATION_SIZE; ++i) {
			totalFitness += evalList[i] / 25;
		}
		
		int fOnN = totalFitness / size;
		int base = getRandom(1, fOnN - 1);
		
		int index = 0;
		int cumulativeFitness = 0;
		
		for (int i = 0; i < size; ++i) {
			int point = base + i * fOnN;
						
			while (cumulativeFitness < point) {
				cumulativeFitness += evalList[index++] / 25;
			}
			
			if (index - 1 >= POPULATION_SIZE) {
				continue;
			}
			for (int j = 0; j < NUM_STUDENTS; ++j) {
				pool[i][j] = population[index - 1][j];
			}
		}
			
		return pool;
	}
	
	private void displayArrangement(int arr[]) {
		for (int i = 0; i < NUM_STUDENTS; ++i) {
			if (arr[i] == 0 || arr[i] == 23 || arr[i] == 58 || arr[i] == 85 || arr[i] == 120 || arr[i] == 129) {
				if (i % NUM_PER_TABLE == NUM_PER_TABLE - 1) {
					System.out.printf("\n");
				}
			} else {
				System.out.printf("%3d%s", arr[i], 
					i % NUM_PER_TABLE == NUM_PER_TABLE - 1 ? "\n" : ", ");
			}
		}
		System.out.printf("\n");
		displayArrangementStatistics(arr);
		for (int i = 0; i < NUM_PER_TABLE * 5 - 2; ++i) {
			System.out.print('-');
		}
		System.out.print('\n');
	}
	
	private void mutateInPlace(int arr[]) {
		int table1 = getRandom(0, NUM_TABLES);
		int table2 = getDifferentRandom(0, NUM_TABLES, table1);
		
		int seat1 = getRandom(0, NUM_PER_TABLE);
		int seat2 = getRandom(0, NUM_PER_TABLE);
		
		int pos1 = table1 * NUM_PER_TABLE + seat1;
		int pos2 = table2 * NUM_PER_TABLE + seat2;

		int temp = arr[pos1];
		arr[pos1] = arr[pos2];
		arr[pos2] = temp;
	}
	
	int gen;
	int prevBest;
	int generationsStuck;
	boolean previousWasNew;
	
	public class GenerationStatistics {
		int bestFitness;
		int avgFitness;
		int worstFitness;
		
		int[] bestData;
		
		int totalCouples;
		int splitCouples;
		
		boolean terminate;
		
		public GenerationStatistics(boolean termin) {
			terminate = termin;
		}
	}
	
	public GenerationStatistics doGeneration() {
		//evaluate
		int evalList[] = new int[POPULATION_SIZE];
		int best = 0;
		int worst = 0;
		int total = 0;
		for (int i = 0; i < POPULATION_SIZE; ++i) {
			evalList[i] = getFitness(population[i]);
			total += evalList[i];
			if (evalList[i] > evalList[best]) {
				best = i;
			}
			if (evalList[i] < evalList[worst]) {
				worst = i;
			}
		}
		int avg = total / POPULATION_SIZE;
					
		if (evalList[best] > evalList[prevBest]) {
			if (!previousWasNew) {
				for (int i = 0; i < NUM_PER_TABLE * 5 - 2; ++i) {
					System.out.print('-');
				}
			}
			System.out.printf("\nGen %d. Average fitness: %d\n", gen++, avg);
			prevBest = best;
			displayPerPersonStatistics(population[best]);
			displayArrangement(population[best]);
			generationsStuck = 0;
			previousWasNew = true;
			
		} else {
			++gen;
			//System.out.printf("Gen %d. Average fitness: %d (best = %d) %d\n", gen++, avg, evalList[best], generationsStuck);
			previousWasNew = false;
			prevBest = best;
		}

		//check for termination condition
		if (avg > evalList[best] * 91 / 100) {
			generationsStuck += 1;
			if (avg > evalList[best] * 96 / 100) {
				generationsStuck += 3;
				if (avg > evalList[best] * 98 / 100) {
					generationsStuck += 4;
				}
			}
		} else {
			generationsStuck /= 2;
		}
		
		//Whatman GE Healthcare Life Sciences Chromatography Paper 1 CHR 2 x 100m reel CAT no. 3001-614 PB-160x22-BR
		//generate statistics
		GenerationStatistics genStats = new GenerationStatistics(generationsStuck > NUM_STUDENTS * 10 + 200);
		genStats.avgFitness = avg;
		genStats.bestFitness = evalList[best];
		genStats.worstFitness = evalList[worst];
		genStats.bestData = new int[NUM_STUDENTS];
		for (int i = 0; i < NUM_STUDENTS; ++i) {
			genStats.bestData[i] = population[best][i];
		}
		genStats.splitCouples = getNumSplitCouples(genStats.bestData);
		genStats.totalCouples = getNumberOfCouples(genStats.bestData);
		
		//terminate
		if (genStats.terminate) {
			return genStats;
		}
		
		//select
		int selected[][] = stochasticUniversalSampling(MATING_POOL_SIZE, evalList);
		
		//find elites
		int elites[][] = getElites(NUM_DIRECT_ELITES, evalList);
		
		//crossover
		for (int i = 0; i < NUM_DIRECT_ELITES; ++i) {
			population[i*2] = elites[i];
			
			for (int j = 0; j < NUM_STUDENTS; ++j) {
				population[i*2+1][j] = elites[i][j];
			}
			
			for (int j = 0; j < NUM_TABLES / 2 + 4; ++j) {
				mutateInPlace(population[i*2+1]);
			}
		}
		for (int i = NUM_DIRECT_ELITES * 2; i < NUM_DIRECT_ELITES * 2 + ADDITIONAL_RANDOMS; ++i) {
			population[i] = createRandomArrangement();
		}
		for (int i = NUM_DIRECT_ELITES * 2 + ADDITIONAL_RANDOMS; i < POPULATION_SIZE; ++i) {
			int p1 = getRandom(0, MATING_POOL_SIZE);
			int p2 = getDifferentRandom(0, MATING_POOL_SIZE, p1);
			
			population[i] = cycleCrossover(selected[p1], selected[p2]);	
			
			//mutate
			while (getRandom(0, 100) < MUTATION_PERCENTAGE) {
				mutateInPlace(population[i]);
				mutateInPlace(population[i]);
				mutateInPlace(population[i]);
				mutateInPlace(population[i]);

			}
		}
		
		return genStats;
	}
	
	private int getTableNumberOfPerson(int person, int[] arr) {
		for (int i = 0; i < NUM_STUDENTS; ++i) {
			if (arr[i] == person) {
				return i / NUM_PER_TABLE;
			}
		}
		return -person;
	}
	
	int coupleTable[];
	boolean coupleType[];
	
	private int getNumberOfCouples(int arr[]) {
		int count = 0;
		for (int i = 0; i < NUM_STUDENTS; ++i) {
			if (coupleTable[i] == -1) continue;
			++count;
			if (coupleType[i]) count += 3;
		}
		
		return count;
	}
	
	private int getNumSplitCouples(int arr[]) {
		int split = 0;
		int bigsplit = 0;
		for (int i = 0; i < NUM_STUDENTS; ++i) {
			if (coupleTable[i] == -1) continue;

			int partnerTable = getTableNumberOfPerson(coupleTable[i], arr);
			int yourTable = getTableNumberOfPerson(i, arr);

			if (partnerTable != yourTable) {
				++split;
				if (coupleType[i]) {
					++bigsplit;
				}
			}
		}
				
		return split + bigsplit;
	}
	
	public void getCouples() {
		coupleTable = new int[NUM_STUDENTS];
		coupleType = new boolean[NUM_STUDENTS];

		for (int i = 0; i < NUM_STUDENTS; ++i) {
			coupleTable[i] = -1;
			coupleType[i] = false;
		}
		
		int couples = 0;
		//people without prefs don't qualify for couple coupling
		for (int i = 0; i < MAX_STUDENT_NUM_WITH_PREFS_EXCLUSIVE; ++i) {
			if (preferences[i][0] == 0) continue;
			
			//if you are your first preference's first preference
			if (i == preferences[preferences[i][0]][0] && preferences[i][0] < MAX_STUDENT_NUM_WITH_PREFS_EXCLUSIVE) {
				coupleTable[i] = preferences[i][0];
				coupleType[i] = false;	//true;
				couples++;
			}
			//} else if (i == preferences[preferences[i][0]][1] && preferences[i][1] < MAX_STUDENT_NUM_WITH_PREFS_EXCLUSIVE) {
			//	coupleTable[i] = preferences[i][0];
			//	coupleType[i] = false;
			//	couples++;
			//}
		}
		
		System.out.printf("Couples = %d\n", couples);
	}
	
	
	//from https://stackoverflow.com/questions/13218019/generating-permutations-of-an-int-array-using-java-error
	
	int permuteBest = 0;
	int permuteBestArr[];
	public void swap(int[] arr, int i, int j)
	{
	    int tmp = arr[i];
	    arr[i] = arr[j];
	    arr[j] = tmp;
	}
	public void permute(int[] arr, int i, int len)
	{
	    if (i == len)
	    {
	    	if (getTableArrFitness(arr) > permuteBest) {
	    		
		        permuteBest = getTableArrFitness(arr);
		        
		        for (int j = 0; j < arr.length; ++j) {
		        	permuteBestArr[j] = -1;
		        }
		        for (int j = 0; j < len; ++j) {
		        	permuteBestArr[j] = arr[j];
		        }
	    	}
	    	
	        return;
	    }
	    for (int j = i; j < len; j++)
	    {
	        swap(arr, i, j); 
	        permute(arr, i + 1, len);  // recurse call
	        swap(arr, i, j);      // backtracking
	    }
	} 
	
	public int getTableArrFitness(int arr[]) {
		int fit = 1;
		int fit2 = 0;
		
		for (int i = 0; i < arr.length; ++i) {
			int person = arr[i];
			int left = arr[(i - 1 + arr.length) % arr.length];
			int left2 = arr[(i - 2 + arr.length) % arr.length];
			int left3 = arr[(i - 3 + arr.length) % arr.length];
			int right = arr[(i + 1 + arr.length) % arr.length];
			int right2 = arr[(i + 2 + arr.length) % arr.length];
			int right3 = arr[(i + 3 + arr.length) % arr.length];

			int personalFitness = 0;
			
			int k = 0;
			for (int j = 0; j < NUM_PREFS; ++j) {
				if (preferences[person][j] == left || preferences[person][j] == right) {
					//check score based on preference number on the table
					personalFitness += (NUM_PREFS - k + 4) * (NUM_PREFS - k + 3) / 4;
					personalFitness += (NUM_PREFS - j + 4) * (NUM_PREFS - j + 3);
				}
				if (preferences[person][j] == left2 || preferences[person][j] == right2) {
					personalFitness += (NUM_PREFS - k + 2) * (NUM_PREFS - k + 2) / 32;
					personalFitness += (NUM_PREFS - j + 2) * (NUM_PREFS - j + 2) / 2;
				}
				if (preferences[person][j] == left3 || preferences[person][j] == right3) {
					personalFitness += (NUM_PREFS - j + 1) * (NUM_PREFS - j + 1) / 2;
				}
				//only increment preference counter if that person is actually on the table
				for (int l = 0; l < arr.length; ++l) {
					if (preferences[person][j] == arr[l]) {
						++k;
						break;
					}
				}
			}
						
			if (coupleTable[person] == left || coupleTable[person] == right) {
				if (coupleType[person]) {
					personalFitness += 30;
				} else {
					personalFitness += 15;
				}
			}
			
			fit *= (int) Math.sqrt((double)personalFitness + 5);
			fit2 += personalFitness;
		}
		
		fit = (int) Math.sqrt((double)fit + 5);
		fit = (int) Math.sqrt((double)fit + 5);
		
		return fit * 3 + fit2;
	}
	
	public void arrangeWithinTables(int arr[]) {
		for (int i = 0; i < NUM_TABLES; ++i) {
			permuteBest = 0;
			permuteBestArr = new int[NUM_PER_TABLE];
			
			
			int[] table = new int[NUM_PER_TABLE];
			int k = 0;
			for (int j = 0; j < NUM_PER_TABLE; ++j) {
				if (arr[i * NUM_PER_TABLE + j] == 0 || arr[i * NUM_PER_TABLE + j] == 23 ||
					arr[i * NUM_PER_TABLE + j] == 58|| arr[i * NUM_PER_TABLE + j] == 85 ||
					arr[i * NUM_PER_TABLE + j] ==120|| arr[i * NUM_PER_TABLE + j] ==129) {
					continue;
				}
				table[k++] = arr[i * NUM_PER_TABLE + j];
			}
			
			permute(table, 0, k);
			
			for (int j = 0; j < permuteBestArr.length; ++j) { 
				System.out.printf("%3d, ", permuteBestArr[j]);
			}
			System.out.printf("\n");
		}
	}
	
	public void arrangeTablesWithinRoom(int arr[]) {
		int scoreTable[][] = new int[NUM_TABLES][NUM_TABLES];
		for (int main = 0; main < NUM_TABLES; ++main) {
			for (int next = 0; next < NUM_TABLES; ++next) {
				if (main == next) continue;
				
				int score = 0;
				for (int i = 0; i < NUM_PER_TABLE; ++i) {
					int personalScore = 0;
					for (int j = 0; j < NUM_PER_TABLE; ++j) {
						for (int k = 0; k < NUM_PREFS; ++k) {
							if (preferences[main * NUM_PER_TABLE + i][k] == arr[next * NUM_PER_TABLE + j]) {
								personalScore += (NUM_PREFS - k);
							}
						}
					}
					
					score += personalScore * personalScore;
				}
				
				score /= 10;
				scoreTable[main][next] = score;
			}
		}
		
		for (int i = 0; i < NUM_TABLES; ++i) {
			for (int j = i + 1; j < NUM_TABLES; ++j) {
				if (i == j) continue;
				
				int score = scoreTable[i][j] * scoreTable[j][i];
				
				System.out.printf("%d, %d, %d\n", i, j, score);
			}
		}
	}
	
	public void start(int popsize, int mut, int mat, int eli) {
		
		
		POPULATION_SIZE = popsize;
		MUTATION_PERCENTAGE = mut;
		MATING_POOL_SIZE = POPULATION_SIZE * mat / 100;
		NUM_DIRECT_ELITES = POPULATION_SIZE * eli / 1000;
		ADDITIONAL_RANDOMS = 0;// POPULATION_SIZE / 2000;
		
		
		getCouples();
		
		System.out.printf("mutations = %d\nmating = %d\nelites = %d\n", MUTATION_PERCENTAGE, MATING_POOL_SIZE, NUM_DIRECT_ELITES);
		
		rng = new Random();
		
		
		population = new int[POPULATION_SIZE][NUM_STUDENTS];
		for (int i = 0; i < POPULATION_SIZE; ++i) {
			population[i] = createRandomArrangement();
		}

		gen = 1;
		prevBest = 0;
		generationsStuck = 0;
		previousWasNew = false;
		
	}
}

